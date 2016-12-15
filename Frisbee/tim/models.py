from django.db import models

from kategoriaTurnaju.models import KategoriaTurnaju
from klub.models import Klub
from django.utils.encoding import smart_unicode

class Tim(models.Model):
    kategoria_turnaju = models.ForeignKey(KategoriaTurnaju)
    umiestnenie = models.PositiveSmallIntegerField(default=1, blank=True, null=True)
    nazov = models.CharField(max_length = 50, default = '')
    klub = models.ForeignKey(Klub, null=True, blank=True)
    spirit = models.BooleanField(default=True)
    
    class Meta:
        verbose_name_plural = 'Timy'
        app_label ="frisbee"
        
    def __str__(self):
        return self.nazov
    
    def __repr__(self):
        return self.nazov
    
    def __unicode__(self): 
        return smart_unicode(self.nazov)