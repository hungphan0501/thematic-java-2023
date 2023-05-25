// Begin: Login and Register

const modal = document.querySelector('.js-modal');
const modalLogin = document.querySelector('.js-login');
const modalClose = document.querySelector('.js-modal-close');
const modalContainer = document.querySelector('.js-modal-container');
const modalLoginBtn = document.querySelector('.js-login-modal');

// hàm hiển thị phần đăng nhập
function showBuyLogin() {
    modal.classList.add('open')
}
// hàm ẩn modal đăng nhập
function hideBuyLogin() {
    modal.classList.remove('open')
}
modalLogin.addEventListener('click', showBuyLogin)

modalClose.addEventListener('click', hideBuyLogin)
    //
modal.addEventListener('click', hideBuyLogin)
    //
modalContainer.addEventListener('click', function(even) {
    even.stopPropagation()
})

const register = document.querySelector('.js_register')
const modalRegister = document.querySelector('.js-modal-register');
const modalCloseRegister = document.querySelector('.js-modal-close-register');
const modalContainerRegister = document.querySelector('.js-modal-container-register');
const modalRegisterBtn = document.querySelector('.js-register-modal')

// hiện thị phần đăng ký
function showModalRegister() {
    modalRegister.classList.add('open')
}

// hàm ẩn modal đăng kí
function hideModalRegister() {
    modalRegister.classList.remove('open')
}

register.addEventListener('click', showModalRegister)
    // modalRegisterBtn.addEventListener('click', showModalRegister)
modalCloseRegister.addEventListener('click', hideModalRegister)
modalRegister.addEventListener('click', hideModalRegister)

modalRegisterBtn.addEventListener('click', showModalRegister)
modalRegisterBtn.addEventListener('click', hideBuyLogin)
modalLoginBtn.addEventListener('click', showBuyLogin)
modalLoginBtn.addEventListener('click', hideModalRegister)


modalContainerRegister.addEventListener('click', function(even) {
    even.stopPropagation()
})
// End: Login and Register

// Begin: Tăng giảm số lượng
function reduceValue() {
    var result = document.getElementById('number'); 
    var qty = result.value;
     if( !isNaN(qty) && qty > 1 ) 
     result.value--;
     return false;
}
 function incrementValue() {
    var result = document.getElementById('number'); 
    var qty = result.value;
     if( !isNaN(qty)) 
     result.value++;
     return false;
 }

 // End: Tăng giảm số lượng

//  Begin: Xử lý button chọn màu
const btnBlack = document.querySelector('.js-color-black');
const btnPink = document.querySelector('.js-color-black');
const btnBule = document.querySelector('.js-color-black');


// xử lý message
const btnMess = document.querySelector('.js-open-mess');
const btnCloseMess = document.querySelector('.js-close-mess');
const message = document.querySelector('.message');

function showMess() {
    message.classList.add('open');
}

function hideMess() {
    message.classList.remove('open');
}

btnMess.addEventListener('click', showMess);
btnCloseMess.addEventListener('click', hideMess);