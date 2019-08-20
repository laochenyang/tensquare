package com.tensquare.qa.controller;

import com.tensquare.qa.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/reply")
public class ReplyController {
    @Autowired
    private ReplyService replyService;
}
