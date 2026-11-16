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

document.addEventListener('DOMContentLoaded', () => {
    const sidebar = document.querySelector('.sidebar');
    const toggle = document.querySelector('.mobile-menu-toggle');

    if (!sidebar || !toggle) {
        return;
    }

    const menu = document.getElementById(toggle.getAttribute('aria-controls'));

    if (!menu) {
        return;
    }

    const closeMenu = () => {
        sidebar.classList.remove('is-open');
        toggle.setAttribute('aria-expanded', 'false');
        toggle.setAttribute('aria-label', 'Open profile and navigation options');
    };

    toggle.addEventListener('click', () => {
        const isOpen = sidebar.classList.toggle('is-open');
        toggle.setAttribute('aria-expanded', String(isOpen));
        toggle.setAttribute(
            'aria-label',
            isOpen
                ? 'Close profile and navigation options'
                : 'Open profile and navigation options'
        );
    });

    menu.addEventListener('click', event => {
        if (event.target.closest('a')) {
            closeMenu();
        }
    });

    document.addEventListener('click', event => {
        if (!sidebar.contains(event.target)) {
            closeMenu();
        }
    });

    document.addEventListener('keydown', event => {
        if (event.key === 'Escape' && sidebar.classList.contains('is-open')) {
            closeMenu();
            toggle.focus();
        }
    });
});
