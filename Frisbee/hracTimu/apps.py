#-*- coding: utf-8 -*-
from django.apps import AppConfig
from django.utils.encoding import smart_unicode

class YourAppConfig(AppConfig):
    name = 'hracTimu'
    verbose_name = smart_unicode('Súťaže')