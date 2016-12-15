from django.contrib import admin
import nested_admin
from models import Tim
from hracTimu.admin import HracTimuAdmin
from hrac.admin import HracAdmin
from zapas.admin import ZapasAdmin2


class TimAdminSelf(nested_admin.NestedAdmin):
    list_display = ['nazov','kategoria_turnaju', 'spirit', 'klub']
    list_filter = ['kategoria_turnaju', 'klub', 'spirit']
    search_fields = ['nazov']
    inlines = [HracTimuAdmin, ZapasAdmin2]

admin.site.register(Tim, TimAdminSelf)


class TimAdmin(nested_admin.NestedStackedInline):
    model = Tim
    extra = 0
    inline_classes = ('grp-collapse grp-open',)

