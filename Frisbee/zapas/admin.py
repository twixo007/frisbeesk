from django.contrib import admin
import nested_admin
from models import Zapas


class ZapasAdminSelf(nested_admin.NestedAdmin):
    list_display = ['kategoria_turnaju', 'tim_1', 'tim_2', 'vysledok_1', 'vysledok_2']
    list_filter = ['kategoria_turnaju']
    search_fields = ['tim_1', 'tim_2']

admin.site.register(Zapas, ZapasAdminSelf)    
    
class ZapasAdmin(nested_admin.NestedStackedInline):
    model = Zapas
    extra = 0
    inline_classes = ('grp-collapse grp-open',)

    
class ZapasAdmin2(nested_admin.NestedStackedInline):
    model = Zapas
    fk_name = 'tim_1'
    extra = 0
    inline_classes = ('grp-collapse grp-open',)
