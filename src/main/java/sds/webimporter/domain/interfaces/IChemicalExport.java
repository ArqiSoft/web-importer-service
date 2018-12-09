package sds.webimporter.domain.interfaces;

import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sds.webimporter.domain.models.Record;

public interface IChemicalExport {
    void Export(Iterator<Record> records, OutputStream otputStream, List<String> properties, Map<String, String> map);

}
