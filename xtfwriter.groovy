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
TRANSFER(xmlns: "http://www.interlis.ch/INTERLIS2.3") {
    HEADERSECTION(SENDER: "some-groovy-fairy-dust", VERSION: "2.3") {
        MODELS {
            MODEL(NAME: "CoordSys", VERSION: "2015-11-24", URI: "http://www.interlis.ch/models")
            MODEL(NAME: "GeometryCHLV03_V1", VERSION: "2017-12-04", URI: "http://www.geo.admin.ch")
            MODEL(NAME: "GeometryCHLV95_V1", VERSION: "2017-12-04", URI: "http://www.geo.admin.ch")
            MODEL(NAME: "SO_AFU_Abbaustellen_20200918", VERSION: "2020-09-18", URI: "http://afu.so.ch")
        }
    }
    DATASECTION {
        'SO_AFU_Abbaustellen_20200918.Abbaustellen'(BID: "bX") {
            abbaustellen.each { abbauObj -> 
                'SO_AFU_Abbaustellen_20200918.Abbaustellen.Abbaustelle'(TID: abbauObj.tid) {
                    Nummer(abbauObj.nummer)
                    Name(abbauObj.name)
                    Bemerkung(abbauObj.bemerkungen)
                    Geometrie(REF: abbauObj.geomRef)
                }
            }
        }
    }
}
"""
TemplateConfiguration config = new TemplateConfiguration();         
config.setAutoIndent(true)
config.setAutoNewLine(true)
def abbaustellenXml = new MarkupTemplateEngine(config).createTemplate(template).make(model)


println abbaustellenXml