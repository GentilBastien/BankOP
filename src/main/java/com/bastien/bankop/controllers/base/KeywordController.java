package com.bastien.bankop.controllers.base;

import com.bastien.bankop.controllers.AbstractEntityController;
import com.bastien.bankop.dto.base.KeywordDTO;
import com.bastien.bankop.entities.base.Keyword;
import com.bastien.bankop.services.base.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/keywords")
public class KeywordController extends AbstractEntityController<KeywordDTO, Keyword, Long> {

    @Autowired
    public KeywordController(KeywordService service) {
        super(service);
    }
}
