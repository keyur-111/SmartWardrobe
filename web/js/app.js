document.addEventListener('DOMContentLoaded',()=>{
document.querySelectorAll('form').forEach(f=>{
f.addEventListener('submit',()=>{
const b=f.querySelector('button[type="submit"],button.btn.primary');
if(b&&f.checkValidity()){
b.dataset.old=b.innerHTML;
b.innerHTML='Working...';
b.disabled=true;
}

}
)
}
);
}
);
