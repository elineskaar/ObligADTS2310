package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void test_initDB(){

        when(repository.initDB(any())).thenReturn("OK");

        String resultat = bankController.initDB();

        assertEquals("OK", resultat);
    }

    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_LoggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentTransaksjoner_LoggetInn() {
        // arrange
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK",null);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentTransaksjoner(anyString(),anyString(),anyString())).thenReturn(konto1);

        Konto resultat = bankController.hentTransaksjoner("01010110523","02.02.2024","10.02.2024");

        assertEquals(konto1, resultat);
    }

    @Test
    public void hentTransaksjoner_IkkeLoggetInn() {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        Konto resultat = bankController.hentTransaksjoner("01010110523","02.02.2024","10.02.2024");

        assertNull(resultat);
    }

    @Test
    public void hentSaldi_LoggetInn() {
        // arrange
        List <Konto> saldi = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK",null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);

        saldi.add(konto1);
        saldi.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentSaldi(anyString())).thenReturn(saldi);

        List <Konto> resultat = bankController.hentSaldi();

        assertEquals(saldi, resultat);
    }

    @Test
    public void hentSaldi_IkkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        List <Konto> resultat = bankController.hentSaldi();

        assertNull(resultat);
    }

    @Test
    public void registrerBetaling_LoggetInn() {
        // arrange
    Transaksjon transaksjon1 = new Transaksjon(1234,"12344556678", 1000, "02.02.2024",
            "Bankoverføring", "Avventer", "09876543542");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn("Godkjent");

        String resultat = bankController.registrerBetaling(transaksjon1);

        assertEquals("Godkjent", resultat);
    }

    @Test
    public void registrerBetaling_IkkeLoggetInn() {
        // arrange
        Transaksjon transaksjon1 = new Transaksjon(1234,"12344556678", 1000, "02.02.2024",
                "Bankoverføring", "Avventer", "09876543542");
        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = bankController.registrerBetaling(transaksjon1);

        assertNull(resultat);
    }

    @Test
    public void hentBetalinger_LoggetInn() {
        // arrange
        List <Transaksjon> transaksjoner = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1234,"12344556678", 1000, "02.02.2024",
                "Bankoverføring", "Avventer", "09876543542");
        Transaksjon transaksjon2 = new Transaksjon(5678,"12344556623", 5050, "08.09.2023",
                "Bankoverføring", "Utført", "09876543518");

        transaksjoner.add(transaksjon1);
        transaksjoner.add(transaksjon2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        List <Transaksjon> resultat = bankController.hentBetalinger();

        assertArrayEquals(transaksjoner.toArray(), resultat.toArray());
    }

    @Test
    public void hentBetalinger_IkkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        List <Transaksjon> resultat = bankController.hentBetalinger();

        assertNull(resultat);
    }

    @Test
    public void utforBetaling_LoggetInn() {
        // arrange
        List <Transaksjon> transaksjoner = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1234,"12344556678", 1000, "02.02.2024",
                "Bankoverføring", "Avventer", "09876543542");
        Transaksjon transaksjon2 = new Transaksjon(5678,"12344556623", 5050, "08.09.2023",
                "Bankoverføring", "Utført", "09876543518");

        transaksjoner.add(transaksjon1);
        transaksjoner.add(transaksjon2);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.utforBetaling(anyInt())).thenReturn("OK");
        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        List <Transaksjon> resultat = bankController.utforBetaling(1234);

        assertArrayEquals(transaksjoner.toArray(), resultat.toArray());
    }

    @Test
    public void utforBetalinger_IkkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        List <Transaksjon> resultat = bankController.utforBetaling(1234);

        assertNull(resultat);
    }

    @Test
    public void utforBetalinger_IkketxID() {
        // arrange
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.utforBetaling(anyInt())).thenReturn("Feil");

        List <Transaksjon> resultat = bankController.utforBetaling(1234);

        assertNull(resultat);
    }
}

