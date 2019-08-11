package app.db.test;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvURLDataSet;
import org.springframework.core.io.Resource;

import com.github.springtestdbunit.dataset.AbstractDataSetLoader;

/**
 * DBUnitでDataSetをCSVで扱うためのローダー
 * @author aoi
 *
 */
public class CsvDataSetLoader extends AbstractDataSetLoader{
	
	@Override
	protected IDataSet createDataSet(Resource resource) throws Exception {
		return new CsvURLDataSet(resource.getURL());
	}
}
