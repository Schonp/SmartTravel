from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
from apiclient import discovery
from httplib2 import Http
from oauth2client.service_account import ServiceAccountCredentials

SCOPES = ["https://www.googleapis.com/auth/forms.body","https://www.googleapis.com/auth/forms.responses.readonly"]
DISCOVERY_DOC = "https://forms.googleapis.com/$discovery/rest?version=v1"

creds = ServiceAccountCredentials.from_json_keyfile_name('credentials.json', SCOPES)
http = creds.authorize(Http())

form_service = discovery.build(
    "forms",
    "v1",
    http=http,
    discoveryServiceUrl=DISCOVERY_DOC,
    static_discovery=False,
)

# Create your views here.

def index(request):
    

    viaje = "La Montaña"

    pregunta = "Compramos el hotel 5 estrellas"

    NEW_FORM = {
        "info": {
            "title": "Encuesta sobre "+viaje,
        }
    }

    # Request body to add a multiple-choice question
    NEW_QUESTION = {
    "requests": [
        {
            "createItem": {
                "item": {
                    "title": ( ""+pregunta
                    ),
                    "questionItem": {
                        "question": {
                            "required": True,
                            "choiceQuestion": {
                                "type": "RADIO",
                                "options": [
                                    {"value": "Si"},
                                    {"value": "No"},
                                ],
                                "shuffle": True,
                            },
                        }
                    },
                },
                "location": {"index": 0},
            }
        }
    ]
    }

    result = form_service.forms().create(body=NEW_FORM).execute()

    question_setting = form_service.forms().batchUpdate(formId=result["formId"], body=NEW_QUESTION).execute() 

    get_result = form_service.forms().get(formId=result["formId"]).execute()

    id = get_result['formId']

    return HttpResponse(id)


def getUrl(request, id):
    result = form_service.forms().get(formId=id).execute()

    retorno = result["responderUri"]

    return HttpResponse(retorno)

def getRtas(request, id):
    result = form_service.forms().responses().list(formId="1ceIyoFiHeXNONkSkfUhSEt0p7NoWn9kVKSHZqrmy7g0").execute()

    cont_si = 0
    cont_no = 0

    respuestas = result["responses"]
    
    for elements in respuestas:
        valor = extraerValor(elements)
        
        if valor == "Si":
            cont_si = cont_si + 1 
        elif valor == "No":
            cont_no = cont_no + 1   
        else:
            pass
    
    return JsonResponse({
        "Si": cont_si,
        "No": cont_no
    })

def extraerValor(respuestas):
    answers = respuestas["answers"]

    dicc = list(answers.values())
    
    retorno = dicc[0]["textAnswers"]["answers"][0]["value"]

    return retorno
