package net.javaguides.springboot.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import net.javaguides.springboot.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaypalService {
	
	@Autowired
	private APIContext apiContext;

		private List<Cart> cartItems;
		private  int idAddress;

		public Payment createPayment(List<Cart> cartItems,
									 int idAddress,
									 String currency,
									 String cancelUrl,
									 String successUrl) throws PayPalRESTException{
			this.cartItems =cartItems;
			this.idAddress=idAddress;
			Double subTotal = cartItems.stream()
					.mapToDouble(item -> item.getTotalPrice())
					.sum();

			Double total = subTotal +5; // adding shipping cost of $10

			Amount amount = new Amount();
			amount.setCurrency(currency);
			total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
			amount.setTotal(String.format("%.2f", total));

			Transaction transaction = new Transaction();
			transaction.setDescription("Payment for items purchased in the store");
			transaction.setAmount(amount);

			List<Transaction> transactions = new ArrayList<>();
			transactions.add(transaction);

			Payer payer = new Payer();
			payer.setPaymentMethod("paypal");

			Payment payment = new Payment();
			payment.setIntent("sale");
			payment.setPayer(payer);
			payment.setTransactions(transactions);
			RedirectUrls redirectUrls = new RedirectUrls();
			redirectUrls.setCancelUrl(cancelUrl);
			redirectUrls.setReturnUrl(successUrl);
			payment.setRedirectUrls(redirectUrls);

			return payment.create(apiContext);
		}

		public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
			Payment payment = new Payment();
			payment.setId(paymentId);
			PaymentExecution paymentExecute = new PaymentExecution();
			paymentExecute.setPayerId(payerId);
			return payment.execute(apiContext, paymentExecute);
		}

		public List<Cart> getCartItems() {
			return cartItems;
		}

		public int getIdAddress() {
			return idAddress;
		}
}
