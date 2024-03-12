package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
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
public class EnhetstestAdminKundeController {

    @InjectMocks
    // denne skal testes
    private AdminKundeController adminKundeController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentAlle_loggetInn() {
        // arrange
        List<Kunde> kunder = new ArrayList<>();
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");
        Kunde annenKunde = new Kunde("01010110522",
                "Hans", "Hansen", "Osloveien 12", "2345",
                "Oslo", "23567890", "Bøøø");

        kunder.add(enKunde);
        kunder.add(annenKunde);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentAlleKunder()).thenReturn(kunder);

        // act
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertEquals(kunder, resultat);
    }

    @Test
    public void hentAlle_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertNull(resultat);
    }

    @Test
    public void lagreKunde_loggetInn() {
        // arrange
        Kunde innKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.registrerKunde(any(Kunde.class))).thenReturn("OK");

        // act
        String resultat = adminKundeController.lagreKunde(innKunde);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void lagreKunde_IkkeLoggetInn()  {
        // arrange
        Kunde innKunde = new Kunde();

        when(sjekk.loggetInn()).thenReturn("Ikke logget inn");

        // act
        String resultat = adminKundeController.lagreKunde(innKunde);

        // assert
        assertNull(resultat);
    }

    @Test
    public void endre_loggetInn() {
        // arrange
        Kunde innKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        // act
        String resultat = adminKundeController.endre(innKunde);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void endre_IkkeLoggetInn()  {
        // arrange
        Kunde innKunde = new Kunde();

        when(sjekk.loggetInn()).thenReturn("Ikke logget inn");

        // act
        String resultat = adminKundeController.endre(innKunde);

        // assert
        assertNull(resultat);
    }

    @Test
    public void slett_loggetInn() {
        // arrange
        String personnummer = "01010110523";

        when(sjekk.loggetInn()).thenReturn(personnummer);

        when(repository.slettKunde(anyString())).thenReturn("OK");

        // act
        String resultat = adminKundeController.slett(personnummer);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void slett_IkkeLoggetInn()  {
        // arrange
        when(sjekk.loggetInn()).thenReturn("Ikke logget inn");

        // act
        String resultat = adminKundeController.slett("01010110523");

        // assert
        assertNull(resultat);
    }
}
