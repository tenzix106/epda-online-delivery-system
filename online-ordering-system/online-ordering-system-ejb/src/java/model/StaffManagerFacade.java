/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author melvin
 */
@Stateless
public class StaffManagerFacade extends AbstractFacade<StaffManager> {

    @PersistenceContext(unitName = "online-ordering-system-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StaffManagerFacade() {
        super(StaffManager.class);
    }
    
}
