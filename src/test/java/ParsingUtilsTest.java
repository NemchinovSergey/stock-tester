import org.junit.Test;
import utils.ParsingUtils;

import static org.junit.Assert.*;

public class ParsingUtilsTest {

    @Test
    public void testPrepareDoubleValue() throws Exception {
        assertEquals("60487", ParsingUtils.prepareDoubleValue("60 487.0000000"));
        assertEquals("60487", ParsingUtils.prepareDoubleValue("60.487.0000000"));
        assertEquals("60487", ParsingUtils.prepareDoubleValue("60,487.0000000"));
        assertEquals("60487", ParsingUtils.prepareDoubleValue("60,487"));
    }

    @Test
    public void testDetectSeparator() throws Exception {
        assertEquals(";", ParsingUtils.detectSeparator("<TICKER>;<PER>;<DATE>;<TIME>;<OPEN>;<HIGH>;<LOW>;<CLOSE>;<VOL>"));
        assertEquals(",", ParsingUtils.detectSeparator("<TICKER>,<PER>,<DATE>,<TIME>,<OPEN>,<HIGH>,<LOW>,<CLOSE>,<VOL>"));
        assertEquals(".", ParsingUtils.detectSeparator("<TICKER>.<PER>.<DATE>.<TIME>.<OPEN>.<HIGH>.<LOW>.<CLOSE>.<VOL>"));
        assertEquals("\t", ParsingUtils.detectSeparator("<TICKER>\t<PER>\t<DATE>\t<TIME>\t<OPEN>\t<HIGH>\t<LOW>\t<CLOSE>\t<VOL>"));
        assertEquals(" ", ParsingUtils.detectSeparator("<TICKER> <PER> <DATE> <TIME> <OPEN> <HIGH> <LOW> <CLOSE> <VOL>"));
    }
}
