from django.db import models

from kategoriaTurnaju.models import KategoriaTurnaju
from tim.models import Tim
from django.utils.encoding import smart_unicode

class Zapas(models.Model):
    kategoria_turnaju = models.ForeignKey(KategoriaTurnaju)
    tim_1 = models.ForeignKey(Tim, related_name='tim', null=False, blank=False, default=None)
    tim_2 = models.ForeignKey(Tim, related_name='%(class)s_tim', null=False, blank=False, default=None)
    vysledok_1 = models.IntegerField()
    vysledok_2 = models.IntegerField()
    
    class Meta:
        verbose_name_plural = 'Zapasy'
        app_label ="frisbee"
        
    def __str__ (self):
        return str(self.kategoria_turnaju) + ':' + str(self.tim_1) + '-' + str(self.tim_2) 
    
    def __repr__(self):
        return str(self.kategoria_turnaju) + ':' + str(self.tim_1) + '-' + str(self.tim_2) 
    
    def __unicode__(self): 
        return smart_unicode(unicode(self.kategoria_turnaju) + ':' + unicode(self.tim_1) + '-' + unicode(self.tim_2))