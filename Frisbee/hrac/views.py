#-*- coding: utf-8 -*-
from django.shortcuts import render, render_to_response
from .models import Hrac
from hracTimu.models import HracTimu
from kategoriaTurnaju.models import KategoriaTurnaju
from tim.models import Tim
from turnaj.models import Turnaj
from zapas.models import Zapas
import django_tables2 as tables
from django_tables2 import RequestConfig
from django.utils.safestring import mark_safe
from django.core.urlresolvers import reverse
from django.utils.translation import ugettext as _
from django.utils.encoding import smart_unicode



class BaseSimpleTable(tables.Table): 
    krstne_meno = tables.Column(verbose_name= 'Krstné meno',orderable=True)
    priezvisko = tables.Column(verbose_name= 'Priezvisko',orderable=True)
    poznamka = tables.Column(verbose_name= 'Poznámka',orderable=True)
    spirit = tables.Column(verbose_name= 'Počet Spiritov',orderable=False,empty_values=())
    pohlavie = tables.Column(verbose_name= 'Pohlavie',orderable=True,empty_values=())
    
    def render_foto(self,record):
        if record.foto == "" or record.foto is None:
            return mark_safe("<div class='round2'><img src='" +'https://secure.gravatar.com/avatar/ad516503a11cd5ca435acc9bb6523536?s=320'+ "'></div>")
        else:
            return mark_safe("<div class='round2'><img src='" +smart_unicode(record.foto)+ "'></div>")
     
    def render_klub(self,record):
        return mark_safe("<a href='klub_hrac=" +str(record.klub.id)+ "'>"+ smart_unicode(record.klub.nazov) +"</a>")   
    
    def render_spirit(self,record):
        hracitimu = HracTimu.objects.filter(hrac = record.id).values('tim')
        timy = Tim.objects.filter(id__in=hracitimu).values('zapas_tim')
        spirity = timy.filter(spirit=True)
        return str(len(spirity))
         
    class Meta:
        model = Hrac
        fields = ('foto','priezvisko', 'pohlavie','krstne_meno','poznamka')
        attrs = {"class": "paleblue"}
        orderable = True



class SimpleTable(BaseSimpleTable):
    foto = tables.Column(verbose_name= 'Foto',orderable=True,empty_values=())
    prezivka = tables.LinkColumn('turnaj_hraca', args=[tables.A('id')], orderable=True, empty_values=(), verbose_name= 'Prezívka')
    klub = tables.Column(verbose_name= 'Klub',orderable=True)
    
    
    class Meta:
        model = Hrac
        fields = ('foto','prezivka','priezvisko', 'pohlavie', 'krstne_meno','klub','poznamka')
        attrs = {"class": "paleblue"}
        orderable = True
    
# Create your views here.
class SimpleTableKlikolNaKlub(BaseSimpleTable):
    foto = tables.Column(verbose_name= 'Foto',orderable=True,empty_values=())
    prezivka = tables.LinkColumn('turnaj_hraca', args=[tables.A('id')], orderable=True, empty_values=(), verbose_name= 'Prezívka')
    
    class Meta:
        model = Hrac
        fields = ('foto','prezivka','priezvisko', 'pohlavie', 'krstne_meno','poznamka')
        attrs = {"class": "paleblue"}
        orderable = True

def hrac(request):
    queryset = Hrac.objects.all()
    nazov = 'Hráči'
    obsah = mark_safe("<h1>" + nazov + "</h1><section>Zobrazenie všetkých hráčov </section>")
    table = SimpleTable(queryset)
    RequestConfig(request).configure(table)
    return render_to_response("table.html", {"table": table,"nazov": nazov,"obsah":obsah })

from turnaj.views import SimpleTable as SimpleTableTurnaj

def turnaj_hraca (request, id):
    button = mark_safe('''
    <form action="#" method="get">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="//code.jquery.com/jquery-1.10.2.js"></script>
    <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <link rel="stylesheet" href="/resources/demos/style.css">
    <script>
    $(function() {
        $( "#start" ).datepicker({ dateFormat: 'dd/mm/yy', showOn: "both", buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif", buttonImageOnly: true}).datepicker("setDate", -365);
        $( "#end" ).datepicker({ dateFormat: 'dd/mm/yy', showOn: "both", buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif", buttonImageOnly: true }).datepicker("setDate", new Date());
    });
    </script>
  
    <p>
        From: <input type="text" id="start" name="start">
        To: <input type="text" id="end" name="end">
        <input type="submit" class="btn" value="Filter" name="mybtn">
    </p>
  
    </form>
    ''')
    
    nazov = smart_unicode("Turnaje Hráča")
    hracitimu = HracTimu.objects.filter(hrac = id).values('tim')
    timy = Tim.objects.filter(id__in=hracitimu).values('kategoria_turnaju')
    kategorieTurnajov = KategoriaTurnaju.objects.filter(id__in=timy).values('turnaj')
    hrac = Hrac.objects.filter(id=id)
    queryset = Turnaj.objects.filter(id__in=kategorieTurnajov)
    
    if request.GET.get('mybtn') and request.GET.get("start") and request.GET.get("end"):
        od = request.GET.get("start")
        od = od.split("/")
        od = [od[2], od[1], od[0]]
        od = "-".join(od)
        do = request.GET.get("end")
        do = do.split("/")
        do = [do[2], do[1], do[0]]
        do = "-".join(do)
        queryset= queryset.filter(datum_od__range=[od,do])
    
    for turnaj in queryset:
        kategorieturnaju = KategoriaTurnaju.objects.filter(turnaj=turnaj.id)
        turnaj.kategoria = kategorieturnaju
    table = SimpleTableTurnaj(queryset)
    RequestConfig(request).configure(table)
    obsah = None
    if hrac[0].foto != "":
        hracitimu = HracTimu.objects.filter(hrac = id).values('tim')
        timy = Tim.objects.filter(id__in=hracitimu).values('zapas_tim')
        spirity = timy.filter(spirit=True)
        pocetSpiritov = len(spirity)
        meno = 'Meno: ' + smart_unicode(hrac[0].krstne_meno) + ' ' + smart_unicode(hrac[0].priezvisko) + '<br>'
        klub = 'Klub: ' + smart_unicode(hrac[0].klub) + '<br>'
        spirit = 'Spirit: ' + str(pocetSpiritov) + '<br>'
        pohlavie = 'Pohlavie: ' + smart_unicode(hrac[0].pohlavie) + '<br>'
        profil = "<div class='profil'>" + '<h3>Profil</h3>'+ meno + klub + pohlavie + spirit + '</div>'
        obsah = mark_safe("<h1>" + nazov + " " +smart_unicode(hrac[0].prezivka) + "</h1><section><div class='round'><img src='" + smart_unicode(hrac[0].foto) + "'></div> " + profil + " </section>")
    else:
        hracitimu = HracTimu.objects.filter(hrac = id).values('tim')
        timy = Tim.objects.filter(id__in=hracitimu).values('zapas_tim')
        spirity = timy.filter(spirit=True)
        pocetSpiritov = len(spirity)
        meno = 'Meno: ' + smart_unicode(hrac[0].krstne_meno) + ' ' + smart_unicode(hrac[0].priezvisko) + '<br>'
        klub = 'Klub: ' + smart_unicode(hrac[0].klub) + '<br>'
        spirit = 'Spirit: ' + str(pocetSpiritov) + '<br>'
        pohlavie = 'Pohlavie: ' + smart_unicode(hrac[0].pohlavie) + '<br>'
        profil = "<div class='profil'>" + '<h3>Profil</h3>'+ meno + klub + pohlavie + spirit + '</div>'
        obsah = mark_safe("<h1>" + nazov + " " +smart_unicode(hrac[0].prezivka) + "</h1><section><div class='round'><img src='" + 'https://secure.gravatar.com/avatar/ad516503a11cd5ca435acc9bb6523536?s=320' + "'></div> " + profil + " </section>")
    return render_to_response("table.html", {"table": table,"nazov": nazov,"obsah":obsah, "button":button})
   

def hraci_klubu(request,id):
    nazov = smart_unicode('Hráči Klubu')
    queryset = Hrac.objects.filter(klub = id)
    table = SimpleTableKlikolNaKlub(queryset)
    obsah = None
    if len(queryset) != 0:
        nazov_klubu = None
        if queryset[0].klub is not None:
            nazov_klubu = smart_unicode(queryset[0].klub)
            obsah = mark_safe("<h1>" + nazov + " "+ nazov_klubu +"</h1><section>"+ smart_unicode('Zobrazenie všetkých Klubov') +"</section>")
        else:
           obsah = mark_safe("<h1>NEEXISTUJÚ HRÁČI PRE DANÝ KLUB</h1>") 
    else:
        obsah = mark_safe("<h1>NEEXISTUJÚ HRÁČI PRE DANÝ KLUB</h1>")
    RequestConfig(request).configure(table)
    return render_to_response("table.html", {"table": table,"nazov": nazov,"obsah":obsah})
    