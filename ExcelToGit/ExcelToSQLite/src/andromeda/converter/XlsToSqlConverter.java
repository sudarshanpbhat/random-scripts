package andromeda.converter;

public class XlsToSqlConverter {

	public static void main(String args[]) {
		XLSReader mXlsReader = new XLSReader("D:\\Programming\\temp\\pincode.xls");
		SQLiteWrapper sqliteWrapper = new SQLiteWrapper("pincode_db.db", "pincode");

		if(mXlsReader.hasNext()) {
			sqliteWrapper.createTable(mXlsReader.getNext());
		}

		while(mXlsReader.hasNext()) {
			sqliteWrapper.insertRow(mXlsReader.getNext());
		}
	}
}
