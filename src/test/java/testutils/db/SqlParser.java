package testutils.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface SqlParser {

	List<Sql> parse(InputStream in) throws IOException ;

}
