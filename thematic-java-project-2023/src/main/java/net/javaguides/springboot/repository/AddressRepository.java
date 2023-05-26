package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    List<Address> getAllByIdUser(int idUser);

    @Query("SELECT a FROM Address a WHERE a.id=?1")
    Address getAddressById(int id);

    List<Address> getAddressByIdUser(int idUser);
}
