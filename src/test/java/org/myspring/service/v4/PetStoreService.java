package org.myspring.service.v4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.myspring.beans.factory.annotation.Autowired;
import org.myspring.dao.v4.AccountDao;
import org.myspring.dao.v4.ItemDao;
import org.myspring.stereotype.Component;

/**
 * @author yujiangtao
 * @date 2018/6/22 20:27
 */
@Component(value="petStore")
@RunWith(Suite.class)
public class PetStoreService {
    @Autowired
    private AccountDao accountDao;

    @Autowired
    private ItemDao itemDao;

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

}
