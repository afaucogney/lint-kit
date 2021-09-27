# TODO

[ ] Detect that ViewModel bind to Activity is named xxxActivityViewModel
[x] Detect every viewmodel function can only support LiveData return types
[x] Detect that viewmodel do not expose callback in its Api
[x] Detect that feature contract interface are well named
[ ] Detect that name are not present in namespace
[ ] Detect that import are not present in namespace
[ ] Detect that a ViewModel bind to Activity lifecycle has not Activity in its name
[ ] Detect that a ViewModel is instanciated outside of a ViewModelProvider
[ ] Detect usage of project forbiden api (observeForEver, subscribe)
[ ] Detect non usage of subscribeOn when Rx stream is created (just, flatmap, switchmap....)

# Backlog

ne pas acvoir de call back dans les viewmodes
ne pas avoir trop de complexite dans les vm
pas d'entity dans les vm
pas de get dans les vms
obsereLiveData peux etre une val !

goTo => navifgation
show viewCapabilities
on ViewEvents
pas d'activity fragment dans viewmodel
send dans ViewTag
mutli impls d un class : base object a la fin !
utilisartion des getActivitViewModel avec type reifier
Instancier un vm sans provider
viewState avant requesstViewState dans VM
subsribe en rx
observeForEver livedata

