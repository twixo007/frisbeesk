from django.contrib import admin
import nested_admin
from super_inlines.admin import SuperInlineModelAdmin, SuperModelAdmin
from turnaj.models import Turnaj
from tim.models import Tim
from kategoriaTurnaju.models import KategoriaTurnaju
from hracTimu.models import HracTimu
from kategoriaTurnaju.admin import KategoriaTurnajuAdmin
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
    
    
class KategoriaTurnajuInlineAdmin(nested_admin.NestedStackedInline):
    model = KategoriaTurnaju
    
    inlines = (TimInlineAdmin,ZapasInlineAdmin, )
    extra = 0
    inline_classes = ('grp-collapse grp-open',)
   

class TurnajAdmin(nested_admin.NestedAdmin):
    list_display = ['nazov','datum_od', 'datum_do', 'mesto', 'stat', 'datum_zapisu', 'report']
    list_filter = ['mesto', 'stat']
    search_fields = ['nazov']
    inlines = [KategoriaTurnajuInlineAdmin,]

admin.site.register(Turnaj, TurnajAdmin)