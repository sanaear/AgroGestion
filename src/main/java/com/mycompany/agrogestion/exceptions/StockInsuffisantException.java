
package com.mycompany.agrogestion.exceptions;


/** La quantité demandée dépasse le stock disponible. */
public class StockInsuffisantException extends AgroException {

    public StockInsuffisantException(String produit, double disponible, double demande) {
        super("Stock insuffisant pour « " + produit + " » : disponible " + disponible
                + ", demandé " + demande + ".");
    }
}