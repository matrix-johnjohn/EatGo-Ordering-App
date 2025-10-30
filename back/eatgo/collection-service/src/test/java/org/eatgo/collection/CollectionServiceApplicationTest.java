package org.eatgo.collection;

import org.eatgo.collection.mapper.CollectionMapper;
import org.eatgo.collection.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CollectionServiceApplicationTest {

    @Autowired
    private CollectionMapper collectionMapper;

    @Autowired
    private CollectionService collectionService;
}
