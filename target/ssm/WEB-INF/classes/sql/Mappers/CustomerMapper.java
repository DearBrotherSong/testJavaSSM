package sql.Mappers;

import test02.Domain.customer.model.CustomerEntity;

import java.util.List;

public interface CustomerMapper {
    public List<CustomerEntity> findAll();
    public String getPassWords(String email);
    public int checkEmail(String email);
    public int register(CustomerEntity.CustomerEntityRegist customer);
}
