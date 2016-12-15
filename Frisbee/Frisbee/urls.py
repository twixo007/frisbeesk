"""Frisbee URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.8/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Add an import:  from blog import urls as blog_urls
    2. Add a URL to urlpatterns:  url(r'^blog/', include(blog_urls))
"""

from django.contrib import admin

admin.autodiscover()

from django.conf.urls import include, url

admin.site.site_header = 'Frisbee Administracia'

from Frisbee.views import index
from hrac.views import hrac, turnaj_hraca, hraci_klubu
from klub.views import klub
from zapas.views import zapas
from turnaj.views import turnaj, zobraz_zapasy_turnaja, zobraz_turnaje_statu, zobraz_timi_turnaja, zobraz_turnaje_mesta
from tim.views import tim, zobraz_hracov_timu, zobraz_hracov_klubu, zobraz_zapasy_timu

urlpatterns = [
    #navigacia
    url(r'^admin/', include(admin.site.urls)),
    url(r'^$', index, name="index"),
    url(r'^index$', index, name="index"),
    url(r'^hraci$', hrac,name="hrac"),
    url(r'^klub$', klub,name="klub"),
    url(r'^zapas$', zapas,name="zapas"),
    url(r'^turnaj$', turnaj,name="turnaj"),
    url(r'^tim$', tim,name="tim"),
    #kliknutia pri turnaji
    url(r'^turnaj_zapas=(?P<id>[0-9]+)$',zobraz_zapasy_turnaja, name='zobraz_zapasy_turnaja'),
    url(r'^turnaj_stat=(?P<stat>.*)$', zobraz_turnaje_statu, name='zobraz_turnaje_statu'),
    url(r'^turnaj_tim=(?P<id_turnaja>[0-9]+)$', zobraz_timi_turnaja, name='zobraz_timi_turnaja'),
    url(r'^turnaj_mesto=(?P<mesto>.*)$', zobraz_turnaje_mesta, name='zobraz_turnaje_mesta'),
    # kliknutie pri timoch
    url(r'^tim_hrac=(?P<id_timu>[0-9]+)$', zobraz_hracov_timu, name='zobraz_hracov_timu'),
    url(r'^tim_klub=(?P<id_klubu>[0-9]+)$', zobraz_hracov_klubu, name='zobraz_hracov_klubu'),
    url(r'^tim_zapas=(?P<id_timu>.*)$', zobraz_zapasy_timu, name='zobraz_zapasy_timu'),
    # kliknutia pri hracovy
    url(r'^turnaj_hraca=(?P<id>[0-9]+)$', turnaj_hraca, name='turnaj_hraca'),
    url(r'^klub_hrac=(?P<id>[0-9]+)$', hraci_klubu, name='hraci_klubu'),
    url(r'^nested_admin/', include('nested_admin.urls')),
    url(r'^grappelli/', include('grappelli.urls')), # grappelli URLS
  
     

    

]
