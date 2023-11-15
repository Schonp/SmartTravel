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

'''def index(request, viaje, pregunta, posiblesRespuestas):

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

    return HttpResponse(id)'''


def index(request, viaje, pregunta, posiblesRespuestas):

    NEW_FORM = {
        "info": {
            "title": "Encuesta sobre " + viaje,
        }
    }

    opciones_respuestas = [{"value": f"{index + 1}. {respuesta.strip()}"} for index, respuesta in enumerate(posiblesRespuestas.split(','))]



    NEW_QUESTION = {
        "requests": [
            {
                "createItem": {
                    "item": {
                        "title": "" + pregunta,
                        "questionItem": {
                            "question": {
                                "required": True,
                                "choiceQuestion": {
                                    "type": "RADIO",
                                    "options": opciones_respuestas,
                                    "shuffle": False,
                                },
                            }
                        },
                    },
                    "location": {"index": 0},
                }
            }
        ]
    }

    # Crear el formulario
    result = form_service.forms().create(body=NEW_FORM).execute()

    # Configurar la pregunta en el formulario
    question_setting = form_service.forms().batchUpdate(formId=result["formId"], body=NEW_QUESTION).execute()

    # Obtener el resultado del formulario
    get_result = form_service.forms().get(formId=result["formId"]).execute()

    id_formulario = get_result['formId']

    return HttpResponse(id_formulario)


def getUrl(request, id):
    result = form_service.forms().get(formId=id).execute()

    retorno = result["responderUri"]

    print(retorno)

    return HttpResponse(retorno)

def getRtas(request, id):
    result = form_service.forms().responses().list(formId=id).execute()

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


def ping(request):
    return HttpResponse("pong")
    

def extraerValor(respuestas):
    answers = respuestas["answers"]

    dicc = list(answers.values())
    
    retorno = dicc[0]["textAnswers"]["answers"][0]["value"]

    return retorno

