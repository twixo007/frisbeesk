#-*- coding: utf-8 -*-
from django.shortcuts import render, render_to_response
from .models import Zapas
import django_tables2 as tables
from django_tables2 import RequestConfig
from django.utils.safestring import mark_safe
from django.core.urlresolvers import reverse
from django.utils.encoding import smart_unicode
from kategoriaTurnaju.models import KategoriaTurnaju
from turnaj.models import Turnaj

class SimpleTable(tables.Table):
    vysledok_1 = tables.Column(verbose_name= 'Výsledok',orderable=True)
    tim_1 = tables.LinkColumn('zobraz_hracov_timu', args=[tables.A('tim_1.id')],verbose_name= 'Domáci',orderable=True)
    tim_2 = tables.LinkColumn('zobraz_hracov_timu', args=[tables.A('tim_2.id')],verbose_name= 'Hostia',orderable=True)
    turnaj = tables.LinkColumn('zobraz_timi_turnaja', args=[tables.A('turnaj.id')],verbose_name= 'Turnaj',orderable=True,empty_values=())
    
    class Meta:
        model = Zapas
        fields = ('turnaj','tim_1','tim_2','vysledok_1')
        attrs = {"class": "paleblue"}
        orderable = True
    
    def render_vysledok_1(self,record):
        return str(record.vysledok_1) + ' : ' + str(record.vysledok_2)
    def render_turnaj(self,record):
        kategoriaTurnaju = KategoriaTurnaju.objects.filter(id=record.kategoria_turnaju.id)
        turnaj = Turnaj.objects.filter(id__in=kategoriaTurnaju)
        return mark_safe("<a href='turnaj_zapas=" +str(turnaj[0].id)+ "'>"+ unicode(turnaj[0]) +"</a>")
    
class SimpleTableOdTurnaja(tables.Table):
    vysledok_1 = tables.Column(verbose_name= 'Výsledok',orderable=True)
    tim_1 = tables.LinkColumn('zobraz_hracov_timu', args=[tables.A('tim_1.id')],verbose_name= 'Domáci',orderable=True)
    tim_2 = tables.LinkColumn('zobraz_hracov_timu', args=[tables.A('tim_2.id')],verbose_name= 'Hostia',orderable=True)
    
    class Meta:
        model = Zapas
        fields = ('tim_1','tim_2','vysledok_1')
        attrs = {"class": "paleblue"}
        orderable = True
        
    def render_vysledok_1(self,record):
        return str(record.vysledok_1) + ' : ' + str(record.vysledok_2)

def zapas(request):
    nazov = "Zápasy"
    queryset = Zapas.objects.all()
    table = SimpleTable(queryset)
    RequestConfig(request).configure(table)
    obsah = mark_safe(smart_unicode("<h1>" + nazov + "</h1><section>Zobrazenie všetkých Zápasou </section>"))
    return render_to_response("table.html", {"table": table,"nazov": nazov,"obsah":obsah})
# Create your views here.
