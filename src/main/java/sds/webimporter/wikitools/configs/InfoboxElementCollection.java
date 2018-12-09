package sds.webimporter.wikitools.configs;

//[ConfigurationCollection(typeof(InfoboxElement), AddItemName = "element")]
import java.util.List;

public class InfoboxElementCollection// : ConfigurationElementCollection
{

    private List<InfoboxElement> infoboxes;

    public List<InfoboxElement> getInfoboxes() {
        return infoboxes;
    }

    protected InfoboxElement CreateNewElement() {
        return new InfoboxElement();
    }

    protected Object GetElementKey(InfoboxElement element) {
        return element.Id;
    }

    public InfoboxElement get(int idx){
        return (InfoboxElement) infoboxes.get(idx);
    }
    
}
