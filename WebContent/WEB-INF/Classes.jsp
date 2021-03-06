<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.beans.Classe"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:GenericLayout>
	<jsp:attribute name="title">
    Classes
  </jsp:attribute>

	<jsp:attribute name="body">
  
  <h1 class=pb-3>Liste des classes</h1>
  
    <table class="table table-hover">
      <thead class="thead-light">
        <tr>
          <th scope="col">#</th>
          <th scope="col">Classe</th>
          <th scope="col">Nombre d'�l�ve dans la classe</th>
          <th scope="col">Modifier</th>
          <th scope="col">Supprimer</th>
        </tr>
      </thead>
      <tbody>
				<c:forEach items="${classes}" var="classe">					
					
				<tr>
			          <th scope="row">${classe.id}</th>
			          <td>${classe.niveau}</td>
			          <td>Inconnu</td>
			          <td><button type="button" class="btn btn-info" data-toggle="modal" data-target="#modalModifierClasse" data-whatever="${classe.id}" id="ajouterClasse">Modifier</button></td>
			          <td><form action="classes" method="POST">
			          	<input type="hidden" value="supprimerClasse" name="action">
			          	<input type="hidden" value="${classe.id}" name="idClasse">			          	
			          	<button type="submit" class="btn btn-danger">Supprimer</button>
			          </form></td>
				</tr>
				</c:forEach>
      </tbody>
    </table>

	
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalAjouterClasse" id="ajouterClasse">Ajouter une classe</button>
	
	<!-- Modal Ajouter une classe -->
    <div class="modal fade" id="modalAjouterClasse" tabindex="-1"
			aria-labelledby="modalAjouterClasseLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="modalAjouterClasseLabel">Ajout d'une classe</h5>
            <button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">

            <form action="classes" method="POST">
            	<input type="hidden" value="ajouterClasse" name="action">
            
              <div class="form-group">
                <label for="nomClasse">Nom de la classe</label>
                <input type="text" class="form-control" id="nomClasse" name="nomClasse" aria-describedby="nomClasseHelp">
                <small id="nomClasseHelp" class="form-text text-muted">Ce nom pourra �tre modifi� plus tard</small>
              </div>
              <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Annuler</button>
              <button type="submit" class="btn btn-primary">Cr�er</button>
            </form>
          </div>
        </div>
      </div>
    </div>
    
    <!-- Modal Modifier une classe -->
    <div class="modal fade" id="modalModifierClasse" tabindex="-1"
			aria-labelledby="modalModifierClasseLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="modalModifierClasseLabel">Modifier une classe</h5>
            <button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">

            <form action="classes" method="POST">
            	<input type="hidden" value="modifierClasse" name="action">
            	<input type="hidden" value="temporaire" id="idClasse_modifier" name="idClasse_modifier">
            
              <div class="form-group">
                <label for="nomClasse">Niveau de la classe</label>
                <input type="text" class="form-control" id="nomClasse" name="nomClasse">
              </div>
              <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Annuler</button>
              <button type="submit" class="btn btn-primary">Modifier</button>
            </form>
          </div>
        </div>
      </div>
    </div>
    
    <p class="mt-3">
			<i>L'ajout d'un �l�ve � une classe se fait dans la section "El�ves" de ce site web.</i>
		</p>
		

	<script>
		$("#modalModifierClasse").on('show.bs.modal', function(event){
			var button = $(event.relatedTarget)
			var recipient = button.data("whatever")
			
			var modal = $(this);
			
			console.log(recipient)
			modal.find("#idClasse_modifier").val(recipient)
		})
	</script>

  </jsp:attribute>
</t:GenericLayout>