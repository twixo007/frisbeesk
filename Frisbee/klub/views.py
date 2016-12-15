# -*- coding: utf-8 -*-
from django.shortcuts import render_to_response, render
from .models import Klub
from hrac.models import Hrac
from hrac.views import SimpleTable as SimpleTableHrac
import django_tables2 as tables
from django_tables2 import RequestConfig
from django.utils.safestring import mark_safe
from django.utils.encoding import smart_unicode

class SimpleTable(tables.Table):
    nazov = tables.LinkColumn('hraci_klubu', args=[tables.A('id')], orderable=True, empty_values=(), verbose_name= 'Názov')
    
    class Meta:
        model = Klub
        fields = ('nazov', )
        attrs = {"class": "paleblue"}
        orderable = True

# Create your views here.
def klub(request):
    nazov = "Kluby"
    queryset = Klub.objects.all()
    table = SimpleTable(queryset)
    RequestConfig(request).configure(table)
    obsah = mark_safe("<h1>" + nazov + "</h1><section>" + smart_unicode('Zobrazenie všetkých Klubov') +" </section>")
    return render_to_response("table.html", {"table": table,"nazov":nazov,"obsah":obsah},context_instance=RequestContext(request))