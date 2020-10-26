package ohtuesimerkki;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StatisticsTest {
    Reader readerStub = new Reader() {

        public List<Player> getPlayers() {
            ArrayList<Player> players = new ArrayList<>();

            players.add(new Player("Semenko", "EDM", 4, 12));
            players.add(new Player("Lemieux", "PIT", 45, 54));
            players.add(new Player("Kurri", "EDM", 37, 53));
            players.add(new Player("Yzerman", "DET", 42, 56));
            players.add(new Player("Gretzky", "EDM", 35, 89));

            return players;
        }
    };

    Statistics stats;

    @Before
    public void setUp() {
        // luodaan Statistics-olio joka käyttää "stubia"
        stats = new Statistics(readerStub);
    }
    
    @Test
    public void loytaaKurrin() {
        Player kurri = stats.search("Kurri");
        assertEquals("Kurri", kurri.getName());
        assertEquals("EDM", kurri.getTeam());
        assertEquals(37, kurri.getGoals());
        assertEquals(53, kurri.getAssists());
    }
    
    @Test
    public void eiPalautaMitaanJosEiOlemassa() {
        assertEquals(null, stats.search("Selänne"));
    }
    
    @Test
    public void joukkueSisaltaaOikeatNimet() {
        List<Player> tiimi = stats.team("EDM");
        
        ArrayList<String> oikeat = new ArrayList<>();
        oikeat.add("Semenko");
        oikeat.add("Kurri");
        oikeat.add("Gretzky");
        
        for (Player pelaaja : tiimi) {
            assertTrue(oikeat.contains(pelaaja.getName()));
        }
    }
    
    @Test
    public void topScorersToimiiNegatiivisella() {
        assertEquals(new ArrayList<>(), stats.topScorers(-1));
    }
    
    @Test
    public void hakeeKaksiParasta() {
        List<Player> parhaat = stats.topScorers(2);
        
        assertEquals(3, parhaat.size());
        
        assertEquals("Gretzky", parhaat.get(0).getName());
        assertEquals("Lemieux", parhaat.get(1).getName());
    }
}
