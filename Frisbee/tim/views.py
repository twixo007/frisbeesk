#-*- coding: utf-8 -*-
from django.shortcuts import render,render_to_response
from .models import Tim
from django.db.models import Q
from hrac.models import Hrac
from zapas.models import Zapas
from turnaj.models import Turnaj
from hracTimu.models import HracTimu
from kategoriaTurnaju.models import KategoriaTurnaju
import django_tables2 as tables
from django_tables2 import RequestConfig
from django.utils.safestring import mark_safe
from django.core.urlresolvers import reverse
from django.utils.encoding import smart_unicode

# Create your views here.

class BaseSimpleTable(tables.Table):
    zapasy = tables.LinkColumn('zobraz_zapasy_timu',args=[tables.A('id')], orderable=False, empty_values=(), verbose_name= 'Zápasy')
    spirit = tables.Column(verbose_name= 'Spirit',orderable=True)
    
    def render_zapasy(self,record):
        return mark_safe('<a href="'+reverse("zobraz_zapasy_timu", args=[record.id])+'">Zobraz</a>')
    
    class Meta:
        model = Tim
        fields = ('umiestnenie', 'spirit')
        attrs = {"class": "paleblue2"}
        orderable = True
        
    def render_klub(self, record):
        return mark_safe("<a href='klub_hrac=" +str(record.klub.id)+ "'>"+ unicode(record.klub.nazov) +"</a>")
        
class SimpleTableKlikolTurnaj(BaseSimpleTable):
    nazov = tables.LinkColumn('zobraz_hracov_timu', args=[tables.A('id')], orderable=True, empty_values=(), verbose_name= 'Názov')
    klub = tables.Column(verbose_name= 'Klub',orderable=True)
    umiestnenie = tables.Column(verbose_name= 'Umiestnenie',orderable=True)

    class Meta:
        model = Tim
        fields = ('umiestnenie','nazov','klub', 'spirit')
        attrs = {"class": "paleblue"}
        orderable = True


          
class SimpleTable(BaseSimpleTable):
    nazov = tables.LinkColumn('zobraz_hracov_timu', args=[tables.A('id')], orderable=True, empty_values=(), verbose_name= 'Názov')
    klub = tables.Column(verbose_name= 'Klub',orderable=True)
    turnaj = tables.Column(verbose_name= 'Turnaj',orderable=True,empty_values=())
    umiestnenie = tables.Column(verbose_name= 'Umiestnenie',orderable=True)
    
    class Meta:
        model = Tim
        fields = ('umiestnenie', 'nazov','turnaj','klub', 'spirit')
        attrs = {"class": "paleblue"}
        orderable = True
        
    def render_turnaj(self, record):
        kategoriaTurnaju = KategoriaTurnaju.objects.filter(id=record.kategoria_turnaju.id)
        turnaj = Turnaj.objects.filter(id__in=kategoriaTurnaju)
        return mark_safe("<a href='turnaj_tim=" +str(turnaj[0].id)+ "'>"+ unicode(turnaj[0]) +"</a>")
    
def tim(request):
    nazov = smart_unicode("Tímy")
    queryset = Tim.objects.all()
    table = SimpleTable(queryset)
    RequestConfig(request).configure(table)
    obsah = mark_safe("<h1>" + nazov + "</h1><section>"+smart_unicode("Zobrazenie všetkých tímov")+ "</section>")
    return render_to_response("table.html", {"table": table,"nazov":nazov,"obsah":obsah})

from hrac.views import SimpleTable as SimpleTableHrac
from hrac.views import SimpleTableKlikolNaKlub
from zapas.views import SimpleTable as SimpleTableZapas

def zobraz_hracov_timu(request, id_timu):
    nazov = smart_unicode("Hráči Tímu")
    query = HracTimu.objects.filter(tim=id_timu).values('hrac')
    samotnyHraci = Hrac.objects.filter(id__in=query)
    table = SimpleTableHrac(samotnyHraci)
    tim = Tim.objects.filter(id=id_timu)
    obsah = mark_safe("<h1>" + nazov + " " + smart_unicode(tim[0].nazov) + "</h1><section>"+smart_unicode("Zobrazenie hráčov daného tímu")+ "</section>")
    RequestConfig(request).configure(table)
    return render_to_response("table.html", {"table": table,"nazov":nazov,"obsah":obsah})

def zobraz_hracov_klubu(request, id_klubu):
    nazov = smart_unicode("Hráči Klubu")
    queryset = Hrac.objects.filter(klub=id_klubu)
    table = SimpleTableKlikolNaKlub(queryset)
    RequestConfig(request).configure(table)
    return render_to_response("table.html", {"table": table,"nazov": nazov})

def zobraz_timi_turnaja(request, id_turnaja):
    nazov = smart_unicode("Tímy Turnaja")
    queryset = Tim.objects.filter(turnaj=id_turnaja)
    table = SimpleTable(queryset)
    RequestConfig(request).configure(table)
    return render_to_response("table.html", {"table": table,"nazov":nazov})

def zobraz_zapasy_timu(request, id_timu):
    nazov = smart_unicode("Zápasy Tímu")
    zapasytimu = Zapas.objects.filter(Q(tim_1=id_timu) | Q(tim_2=id_timu))
    table = SimpleTableZapas(zapasytimu)
    RequestConfig(request).configure(table)
    tim = Tim.objects.filter(id=id_timu)
    obsah = mark_safe("<h1>" + nazov + " " + smart_unicode(tim[0].nazov) + "</h1><section>"+smart_unicode("Zobrazenie zápasov daného tímu")+ "</section>")
    return render_to_response("table.html", {"table": table,"nazov":nazov, 'obsah': obsah})
