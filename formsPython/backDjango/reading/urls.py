from django.urls import path
from . import views

urlpatterns = [
    path('crear/<str:viaje>/<str:pregunta>/<str:posiblesRespuestas>/', views.index, name='index'),
    path('url/<str:id>', views.getUrl, name='getUrl'),
    path('rtas/<str:id>', views.getRtas, name='getRtas'),
    path('ping', views.ping, name='ping')
]

