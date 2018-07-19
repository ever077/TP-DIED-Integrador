package frsf.isi.died.app.dao.util;

import java.util.List;

public interface CsvRecord {

	public List<String> asCsvRow();
	public void loadFromStringRow(List<String> datos);
}
