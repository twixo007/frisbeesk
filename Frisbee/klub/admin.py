from django.contrib import admin
import nested_admin
from models import Klub
from hrac.admin import HracAdmin



class KlubAdmin(nested_admin.NestedAdmin):
    list_display = ['nazov']
    search_fields = ['nazov']
    inlines = [HracAdmin]

admin.site.register(Klub, KlubAdmin)