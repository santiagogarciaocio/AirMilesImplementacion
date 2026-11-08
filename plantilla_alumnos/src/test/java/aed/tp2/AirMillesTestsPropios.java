package aed.tp2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class AirMillesTestsPropios {
    private AirMiles am;

    @BeforeEach
    void setUp() {
        am = new AirMiles();
    }

    @Test
    void sistemaInicializaConPromoNoPromoActivada() {
        am.registrarUsuario(1);        
        am.acumularMillas(1, 100, "NoPromo");
        assertEquals(100, am.consultarSaldo(1));
    }

    @Test 
    void chequearCategorias(){
        am.registrarUsuario(1);
        am.registrarUsuario(2);
        am.registrarUsuario(3);
        am.registrarUsuario(4);
        
        am.acumularMillas(1, 49999, "NoPromo");
        am.acumularMillas(2, 900000, "NoPromo");
        am.acumularMillas(3, 1000000, "NoPromo");
        /*Todas las categorias */
        assertEquals("Bronce", am.consultarCategoria(4));
        assertEquals("Plata", am.consultarCategoria(1));
        assertEquals("Oro", am.consultarCategoria(2));
        assertEquals("Platino", am.consultarCategoria(3));

        /*Acumular millas puede incrementar categoria. Canjear no altera categoria*/
        am.acumularMillas(1, 1, "NoPromo");
        am.acumularMillas(4, 100, "NoPromo");
        am.canjearMillas(2, 850000);
        assertEquals("Bronce", am.consultarCategoria(4));
        assertEquals("Oro", am.consultarCategoria(1));
        assertEquals("Oro", am.consultarCategoria(2));

        /*Recibir via transferencia no incrementa categoria */

        am.transferirMillas(3, 4, 500000);
        assertEquals("Bronce", am.consultarCategoria(4));
        assertEquals("Platino", am.consultarCategoria(3));
    }

    @Test 
    void creacionEliminacionPromos(){
        am.registrarUsuario(1);
        am.registrarUsuario(2);
        am.registrarUsuario(3);
        
        am.iniciarPromocion("SuperPromo", 1.5);       

        am.acumularMillas(1, 1000, "SuperPromo");
        assertEquals(1000*1.5, am.consultarSaldo(1));

        am.iniciarPromocionConTope("EspecialNavidad", 2, 100000);
        am.iniciarPromocionConTope("EspecialAñoNuevo", 2, 1000000);

        am.acumularMillas(2, 100000/2, "EspecialNavidad");
        assertEquals(100000, am.consultarSaldo(2));
        /*Tope de promocion. Eliminacion automatica, default factor 1 */
        am.acumularMillas(3, 100, "EspecialNavidad");
        assertEquals(100, am.consultarSaldo(3));

        am.acumularMillas(2, 10000, "EspecialAñoNuevo");
        assertEquals(100000+10000*2*2, am.consultarSaldo(2));

        am.finalizarPromocion("EspecialAñoNuevo");
        am.acumularMillas(1, 100, "EspecialAñoNuevo");
        assertEquals(1000*1.5+100, am.consultarSaldo(1));   

    }

    @Test
    void masACumuladorMasCanjeadorChequero() {
        am.registrarUsuario(1);
        am.registrarUsuario(2);
        am.registrarUsuario(3);
        
        /*En caso de empate devuelve cualquiera */
        assertEquals(1, am.elMasAcumulador());
        assertEquals(1, am.elMasCanjeador());
        /*SE actualiza correctamente luego de acumular millas */
        am.acumularMillas(3, 1, "NoPromo");
        assertEquals(3, am.elMasAcumulador());
        assertEquals(1, am.elMasCanjeador());
        /*Se actualiza correctamente luedo de canjear millas */
        am.acumularMillas(2, 10000, "NoPromo");
        assertEquals(2, am.elMasAcumulador());
        assertEquals(1, am.elMasCanjeador());
        /*trasnferir millas incrementa las canjeadas, pero no altera parar acumuladas */
        am.transferirMillas(2, 1, 5010);
        assertEquals(2, am.elMasAcumulador());
        assertEquals(2, am.elMasCanjeador());
        /*chequeo final */
        am.acumularMillas(3, 6000, "NoPromo");
        am.canjearMillas(3, 6000);
        assertEquals(2, am.elMasAcumulador());
        assertEquals(3, am.elMasCanjeador());
    }

    @Test
    void rankingMillas() {
        am.registrarUsuario(1);
        am.registrarUsuario(2);
        am.registrarUsuario(3);
        am.registrarUsuario(4);
        am.registrarUsuario(5);

        am.acumularMillas(1, 1000, "NoPromo");
        am.acumularMillas(2, 10000, "NoPromo");
        am.acumularMillas(3, 100000, "NoPromo");
        am.acumularMillas(4, 1000000, "NoPromo");
        am.acumularMillas(5, 10000000, "NoPromo");


        int [] top1 = am.rankingMillas(5);
        assertEquals(5,top1.length);      
        assertEquals(5,top1[0]);
        assertEquals(4,top1[1]);
        assertEquals(3,top1[2]);
        assertEquals(2,top1[3]);
        assertEquals(1,top1[4]);      
        
        /*Canjeao pero no altero el ranking */
        am.canjearMillas(3, 80000);
        int [] top2 = am.rankingMillas(5);
        assertEquals(5,top2.length);      
        assertEquals(5,top2[0]);
        assertEquals(4,top2[1]);
        assertEquals(3,top2[2]);
        assertEquals(2,top2[3]);
        assertEquals(1,top2[4]);  

        /*Se altera el ranking */
        am.canjearMillas(3, 15000);
        am.acumularMillas(2, 1000000, "NoPromo");
        int [] top3 = am.rankingMillas(5);
        assertEquals(5,top3.length);      
        assertEquals(5,top3[0]);
        assertEquals(2,top3[1]);
        assertEquals(4,top3[2]);
        assertEquals(3,top3[3]);
        assertEquals(1,top3[4]); 

        /*Pido menos que el total N  */
        int [] top4 = am.rankingMillas(3);
        assertEquals(3,top4.length);      
        assertEquals(5,top4[0]);
        assertEquals(2,top4[1]);
        assertEquals(4,top4[2]);

        /*Transferencias altera ranking */
        am.transferirMillas(5, 1, 10000000);
        int [] top5 = am.rankingMillas(5);
        assertEquals(5,top5.length);      
        assertEquals(1,top5[0]);
        assertEquals(2,top5[1]);
        assertEquals(4,top5[2]);
        assertEquals(3,top5[3]);
        assertEquals(5,top5[4]);
    }


    @Test
    void rankingMillasNmasQueTotalUsuariosEnSistema() {
        am.registrarUsuario(1);
        am.registrarUsuario(2);
        am.registrarUsuario(3);

        am.acumularMillas(1, 1000, "NoPromo");
        am.acumularMillas(2, 10000, "NoPromo");
        am.acumularMillas(3, 100000, "NoPromo");

        int [] us = am.rankingMillas(5);

        assertEquals(3,us.length);
        assertEquals(3,us[0]);
        assertEquals(2,us[1]);
        assertEquals(1,us[2]);        
    }

    @Test
    void transferidoresExclusivos() {
        am.registrarUsuario(1);
        am.registrarUsuario(2);
        am.registrarUsuario(3);
        am.registrarUsuario(4);
        am.registrarUsuario(5);

        am.acumularMillas(1, 1000, "NoPromo");
        am.acumularMillas(2, 1000, "NoPromo");
        am.acumularMillas(3, 1000, "NoPromo");
        am.acumularMillas(4, 1000, "NoPromo");
        am.acumularMillas(5, 1000, "NoPromo");

        assertEquals(0, am.paresTransferidoresExclusivos().length);

        /*Transferencia Unica establece pareja */
        am.transferirMillas(1,2, 1);
        int [][] par1 = am.paresTransferidoresExclusivos();
        assertEquals(1, par1.length);
        assertEquals(1,par1[0][0]);
        assertEquals(2,par1[0][1]);
        
        /*Transferencias ida y vuelta en pareja */
        am.transferirMillas(5,4, 1);
        am.transferirMillas(4,5, 3);
        int [][] par2 = am.paresTransferidoresExclusivos();
        assertEquals(2, par2.length);
        assertEquals(1,par2[0][0]);
        assertEquals(2,par2[0][1]);
        assertEquals(4,par2[1][0]);
        assertEquals(5,par2[1][1]);

        /*Transferencia con 3 rompe pareja */
        am.transferirMillas(3,4, 2);
        int [][] par3 = am.paresTransferidoresExclusivos();
        assertEquals(1, par3.length);
        assertEquals(1,par3[0][0]);
        assertEquals(2,par3[0][1]);
    }

}
