from django.urls import path
from . import views

urlpatterns = [
    path('', views.index, name='index'),
    path('url/<str:id>', views.getUrl, name='getUrl'),
    path('rtas/<str:id>', views.getRtas, name='getRtas')
]

