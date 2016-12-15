from django.contrib import admin
import nested_admin
from models import Hrac
from django.contrib.auth.models import User
from hracTimu.admin import HracTimuAdmin


class HracAdminSelf(nested_admin.NestedAdmin):
    list_display = ['prezivka','krstne_meno', 'priezvisko', 'pohlavie', 'telefonne_cislo', 'miesto_bydliska', 'datum_narodenia', 'uzivatel', 'klub', 'foto', 'poznamka']
    list_filter = ['klub']
    search_fields = ['prezivka', 'krstne_meno', 'priezvisko']
    inlines = [HracTimuAdmin]
    
admin.site.register(Hrac, HracAdminSelf)


class HracAdmin(nested_admin.NestedStackedInline):
    inline_classes = ('grp-collapse grp-open',)
    model = Hrac
    extra = 0

    
class UserAdmin(nested_admin.NestedAdmin):
    inlines = [HracAdmin]
    
    
admin.site.unregister(User)
admin.site.register(User, UserAdmin)