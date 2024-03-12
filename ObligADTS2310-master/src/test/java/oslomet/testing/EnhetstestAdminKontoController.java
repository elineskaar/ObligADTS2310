package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {

    @InjectMocks
    // denne skal testes
    private AdminKontoController adminKontoController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentAlleKonti_loggetInn() {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK",null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);

        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentAlleKonti()).thenReturn(konti);

        // act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentAlleKonti_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void registrerKonto_LoggetInn() {
        // arrange
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK",null);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.registrerKonto(any(Konto.class))).thenReturn("OK");

        String resultat = adminKontoController.registrerKonto(konto1);

        assertEquals("OK", resultat);
    }

    @Test
    public void registrerKonto_IkkeLoggetInn()  {
        // arrange
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK",null);

        when(sjekk.loggetInn()).thenReturn("Ikke innlogget");

        // act
        String resultat = adminKontoController.registrerKonto(konto1);

        // assert
        assertNull(resultat);
    }

    @Test
    public void endreKonto_LoggetInn() {
        // arrange
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK",null);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.endreKonto(any(Konto.class))).thenReturn("OK");

        String resultat = adminKontoController.endreKonto(konto1);

        assertEquals("OK", resultat);
    }

    @Test
    public void endreKonto_IkkeLoggetInn()  {
        // arrange
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK",null);

        when(sjekk.loggetInn()).thenReturn("Ikke innlogget");

        // act
        String resultat = adminKontoController.endreKonto(konto1);

        // assert
        assertNull(resultat);
    }

    @Test
    public void slettKonto_LoggetInn() {
        // arrange
        String kontonummer = "01010110512";

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.slettKonto(anyString())).thenReturn("OK");

        String resultat = adminKontoController.slettKonto(kontonummer);

        assertEquals("OK", resultat);
    }

    @Test
    public void slettKonto_IkkeLoggetInn()  {
        // arrange
        String kontonummer = "01010110512";

        when(sjekk.loggetInn()).thenReturn("Ikke innlogget");

        // act
        String resultat = adminKontoController.slettKonto(kontonummer);

        // assert
        assertNull(resultat);
    }
}
