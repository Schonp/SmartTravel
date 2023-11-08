from apiclient import discovery
from httplib2 import Http
from oauth2client.service_account import ServiceAccountCredentials

SCOPES = "https://www.googleapis.com/auth/forms.body"
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

viaje = "La Montaña"

NEW_FORM = {
    "info": {
        "title": "Encuesta sobre " + viaje,
    }
}

# Request body to add a multiple-choice question
NEW_QUESTION = {
"requests": [
    {
        "createItem": {
            "item": {
                "title": (
                    "In what year did the United States land a mission on"
                    " the moon?"
                ),
                "questionItem": {
                    "question": {
                        "required": True,
                        "choiceQuestion": {
                            "type": "RADIO",
                            "options": [
                                {"value": "1965"},
                                {"value": "1967"},
                                {"value": "1969"},
                                {"value": "1971"},
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
print(get_result)