package org.phoenicis.lnk;

import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.Assert.*;

public class LnkParserTest {
    private LnkFile game1;
    private LnkFile game2;

    @Before
    public void setUp() throws IOException {
        final InputStream game1inputStream = Resources.getResource("xiii.lnk").openStream();
        final InputStream game2inputStream = Resources.getResource("teenagent.lnk").openStream();

        this.game1 = new LnkParser().parse(game1inputStream);
        this.game2 = new LnkParser().parse(game2inputStream);
    }

    @Test
    public void testLnkParser_testPath() {
        assertEquals("C:\\GOG Games\\XIII\\system\\xiii.exe", game1.getRealFilename());
    }

    @Test
    public void testLnkParser_testPath_dosbox() {
        assertEquals("c:\\gog games\\teenagent\\DOSBOX\\dosbox.exe", game2.getRealFilename());
    }

    @Test
    public void testLnkParser_testIsDirectory() {
        assertFalse(game1.isDirectory());
    }

    @Test
    public void testLnkParser_testHasArguments_noArguments() {
        assertTrue(game1.isHasArguments());
    }

    @Test
    public void testLnkParser_testHasArguments_arguments() {
        assertTrue(game2.isHasArguments());
    }

    @Test
    public void testLnkParser_fetchArguments_example2() {
        assertEquals(
                Optional.of("-conf \"..\\dosboxTeenagent.conf\" -conf \"..\\dosboxTeenagent_single.conf\" -noconsole -c \"exit\""),
                game2.getStringData().getCommandLineArguments());
    }

    @Test
    public void testLnkParser_fetchIconLocation_example2() {
        assertEquals(Optional.of("c:\\gog games\\teenagent\\goggame-1207658753.ico"), game2.getStringData().getIconLocation());
    }

    @Test
    public void testLnkParser_fetchIconLocation_example1() {
        assertEquals(Optional.of("C:\\GOG Games\\XIII\\gfw_high.ico"), game1.getStringData().getIconLocation());
    }
}
