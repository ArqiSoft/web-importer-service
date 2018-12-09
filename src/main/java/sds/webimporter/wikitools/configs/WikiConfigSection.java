package sds.webimporter.wikitools.configs;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
public class WikiConfigSection 
{

    @Value("${wiki.templateNames.templates}")
    public String[] templates;

    public String[] GetTemplates() {
        return templates;
    }

    protected TemplateElement CreateNewElement() {
        return new TemplateElement();
    }

    protected Object GetElementKey(TemplateElement element) {
        return element.Name;
    }

    public String get(int idx) {
        return templates[idx];
    }
    

    public InfoboxCollection WikiBoxes;
    
    public static class InfoboxCollection
    {
        private List<Infobox> infoboxes = new ArrayList<>();

        public List<Infobox> getInfoboxes() {
            return infoboxes;
        }

        protected Infobox CreateNewElement() {
            return new Infobox();
        }

        protected Object GetElementKey(Infobox element) {
            return element.Type;
        }

        public Infobox get(int idx) {
            return infoboxes.get(idx);
        }
    }

}
