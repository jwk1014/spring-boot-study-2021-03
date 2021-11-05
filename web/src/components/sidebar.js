const SideBarComponent = () => (
  <>
      <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

          <a class="sidebar-brand d-flex align-items-center justify-content-center" href="index.html">
              <div class="sidebar-brand-text mx-3">스프링 부트 스터디</div>
          </a>

          <hr class="sidebar-divider" />

          <div class="sidebar-heading">
              Data
          </div>

          <li class="nav-item">
              <a class="nav-link" href="/#/posts">
                  <i class="fas fa-fw fa-chart-area"></i>
                  <span>Posts</span></a>
          </li>

          <li class="nav-item">
              <a class="nav-link" href="tables.html">
                  <i class="fas fa-fw fa-table"></i>
                  <span>Users</span></a>
          </li>

          <hr class="sidebar-divider d-none d-md-block" />

          <div class="text-center d-none d-md-inline">
              <button class="rounded-circle border-0" id="sidebarToggle"></button>
          </div>
      </ul>
  </>
);

export default SideBarComponent;