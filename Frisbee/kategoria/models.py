from django.db import models
from django.utils.encoding import smart_unicode

class Kategoria(models.Model):
    nazov = models.CharField(max_length = 50)
    
    class Meta:
        verbose_name_plural = 'Kategorie'
        app_label ="frisbee"
    
    def __str__(self):
        return self.nazov
    
    def __repr__(self):
        return self.nazov
    
    def __unicode__(self): 
        return smart_unicode(self.nazov)