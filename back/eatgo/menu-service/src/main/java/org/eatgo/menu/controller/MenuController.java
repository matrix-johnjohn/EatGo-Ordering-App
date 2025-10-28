package org.eatgo.menu.controller;

import lombok.RequiredArgsConstructor;
import org.eatgo.menu.service.MenuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;
}
