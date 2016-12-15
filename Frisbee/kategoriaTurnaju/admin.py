from django.contrib import admin

import nested_admin
from .models import KategoriaTurnaju
from hracTimu.models import HracTimu
from tim.models import Tim
from zapas.models import Zapas


class HracInlineAdmin(nested_admin.NestedStackedInline):
    model = HracTimu
    extra = 0
    inline_classes = ('grp-collapse grp-open',)
    



class TimInlineAdmin(nested_admin.NestedStackedInline):
    model = Tim
    inlines = (HracInlineAdmin,)
    extra = 0
    inline_classes = ('grp-collapse grp-open',)
    
    
class ZapasInlineAdmin(nested_admin.NestedStackedInline):
    model = Zapas
    extra = 0
    inline_classes = ('grp-collapse grp-open',)
    


class KategoriaTurnajuAdminSelf(nested_admin.NestedAdmin):
    list_display = ['turnaj', 'kategoria', 'pocet_timov']
    search_fields = ['turnaj', 'kategoria']
    inlines = (TimInlineAdmin,ZapasInlineAdmin,)

admin.site.register(KategoriaTurnaju, KategoriaTurnajuAdminSelf)

class KategoriaTurnajuAdmin(nested_admin.NestedStackedInline):
    model = KategoriaTurnaju
    extra = 0
    inline_classes = ('grp-collapse grp-open',)
