#-*- coding: utf-8 -*-
from django.db import models

from klub.models import Klub
from django.contrib.auth.models import User
from django.utils.encoding import smart_unicode
from django.template.defaultfilters import default
from datetime import date



class Hrac(models.Model):
    uzivatel = models.ForeignKey(User, null=True, blank=True)
    klub = models.ForeignKey(Klub, null=True, blank=True)
    old_id = models.PositiveIntegerField(null=True, blank=True)
    krstne_meno = models.CharField(max_length = 50, null=True, blank=True)
    priezvisko = models.CharField(max_length = 50, null=True, blank=True)
    telefonne_cislo = models.CharField(max_length = 50, null=True, blank=True)
    
    moznosti = (
                (smart_unicode("Muž"), smart_unicode("Muž")),
                (smart_unicode("Žena"), smart_unicode("Žena")),
                )
                
    pohlavie = models.CharField(max_length=5, choices =moznosti, default = "Muž", null=True, blank=True)
    datum_narodenia = models.DateField(default=date.today, null=True, blank=True)
    miesto_bydliska = models.CharField(max_length=255,null=True, blank=True)
    
    
    prezivka = models.CharField(max_length = 50)
    foto = models.CharField(max_length = 250, null=True, blank=True,default='')
    poznamka = models.CharField(max_length = 250, null=True, blank=True)
    
    

    class Meta:
        verbose_name_plural = 'Hraci'
        app_label ="frisbee"
        
    def __str__(self):
        return self.prezivka
    
    def __repr__(self):
        return self.prezivka
    
    def __unicode__(self): 
        return smart_unicode(self.prezivka)