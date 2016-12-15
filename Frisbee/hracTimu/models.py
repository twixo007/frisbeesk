from django.db import models
from django.utils.encoding import smart_unicode

from hrac.models import Hrac
from tim.models import Tim



class HracTimu(models.Model):
    tim = models.ForeignKey(Tim)
    hrac = models.ForeignKey(Hrac)
    
    
    class Meta:
        app_label ="frisbee"
        verbose_name_plural = 'Hraci Timov'
        
    def __str__(self):
        return str(self.hrac) + '->' + str(self.tim)
    
    def __repr__(self):
        return str(self.hrac) + '->' + str(self.tim)
    
    def __unicode__(self): 
        return smart_unicode(unicode(self.hrac) + '->' + unicode(self.tim))

