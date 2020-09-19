import groovy.text.markup.MarkupTemplateEngine
import groovy.text.markup.TemplateConfiguration

class Abbaustelle {
    String tid
    String nummer
    String name
    String bemerkungen
    String geomRef
}

def emailHtmlTemplate = """
html {
    head {
        title('Service Subscription Email')
    }
    body {
        p('Dear Norman')
        p('Thanks for subscribing our services.')
        p('Baeldung')
    }
}"""
def emailHtml = new MarkupTemplateEngine().createTemplate(emailHtmlTemplate).make()

def model = [abbaustellen: [new Abbaustelle(tid: "5c6be6dd-7111-42fd-9eae-bd46fefa3c93", nummer: "5432", name: "Hellstätt", bemerkungen: "Fubar", geomRef: "5e5bb99e-2f68-499e-aebe-d01f05b9ea88")]]

def template = """
xmlDeclaration()
'SO_AFU_Abbaustellen_20200918.Abbaustellen'(BID: 'bX') {
   abbaustellen.each {
       'SO_AFU_Abbaustellen_20200918.Abbaustellen.Abbaustelle'(TID: it.tid, model: it.nummer)
   }
}
"""
TemplateConfiguration config = new TemplateConfiguration();         
config.setAutoIndent(true)
config.setAutoNewLine(true)
def abbaustellenXml = new MarkupTemplateEngine(config).createTemplate(template).make(model)


println abbaustellenXml