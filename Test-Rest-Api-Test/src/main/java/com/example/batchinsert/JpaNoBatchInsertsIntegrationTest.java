package com.example.batchinsert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JpaNoBatchInsertsIntegrationTest
{

    @PersistenceContext
    private EntityManager entityManager;

}
