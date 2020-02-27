<?php

namespace DocumentBundle\Controller;
use DocumentBundle\Entity\Categorie;
use DocumentBundle\Entity\Document;
use DocumentBundle\Entity\Emprunt;
use DocumentBundle\Entity\User;
use DocumentBundle\Form\EmpruntType;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\HttpFoundation\Request;

/**
 * Document controller.
 *
 * @Route("document")
 */
class DocumentController extends Controller
{
    /**
     * Lists all document entities.
     *
     * @Route("/", name="document_index")
     * @Method("GET")
     */
    public function indexAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();

        $documents = $em->getRepository('DocumentBundle:Document')->findAll();

        if($request->getMethod() == Request::METHOD_GET) {
            $name = $request->get('filter');
            $documents = $this->getDoctrine()->getRepository(Document::class)->mefind($name);
        }
        return $this->render('@Document/document/index.html.twig', array(
            'documents' => $documents,
        ));
    }

    /**
     * Lists all document entities.
     *
     * @Route("/indexfront", name="documentfront_index")
     * @Method("GET")
     */
    public function indexfrontAction(Request $request)
    {


        $em = $this->getDoctrine()->getManager();

        $documents = $em->getRepository('DocumentBundle:Document')->findAll();
        $emprunts = $em->getRepository('DocumentBundle:Emprunt')->findAll();

        /**
         * @var $paginator \Knp\Component\Pager\Paginator
         */
        $paginator =$this->get('knp_paginator');
        $documents = $paginator->paginate(
            $documents,
            $request->query->getInt('page',1),
            $request->query->getInt('limit',3)
        );
        return $this->render('@Document/document/indexfront.html.twig', array(
            'documents' => $documents,
            'emprunts' => $emprunts
        ));
    }

    /**
     * Creates a new document entity.
     *
     * @Route("/new", name="document_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request)
    {
        $document = new Document();
        $form = $this->createForm('DocumentBundle\Form\DocumentType', $document);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $file = $form['image']->getData();
            $file->move('images/', $file->getClientOriginalName());
            $document->setImage(''.$file->getClientOriginalName());
            $em->persist($document);
            $em->flush();

            return $this->redirectToRoute('document_show', array('id' => $document->getId()));
        }

        return $this->render('@Document/document/new.html.twig', array(
            'document' => $document,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a document entity.
     *
     * @Route("/{id}", name="document_show")
     * @Method("GET")
     */
    public function showAction(Document $document)
    {
        $deleteForm = $this->createDeleteForm($document);

        return $this->render('@Document/document/show.html.twig', array(
            'document' => $document,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    public function searchAjaxAction(Request $request){
        if($request->isXmlHttpRequest()){
            $name=$request->get('nomdocument');
            $document=$this->getDoctrine()->getRepository(Document::class)->mefind($name);
            //initialisation of serializer
            $se=new Serializer(array(new ObjectNormalizer()));
            //normalizer les listes
            $data=$se->normalize($document);
            return new JsonResponse($data);
        }
        return $this->render('@Document/document/searchAjax.html.twig');
    }
    /**
     * Displays a form to edit an existing document entity.
     *
     * @Route("/{id}/edit", name="document_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, Document $document)
    {
        $deleteForm = $this->createDeleteForm($document);
        $editForm = $this->createForm('DocumentBundle\Form\DocumentType', $document);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('document_edit', array('id' => $document->getId()));
        }

        return $this->render('@Document/document/edit.html.twig', array(
            'document' => $document,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a document entity.
     *
     * @Route("/{id}", name="document_delete")
     * @Method("DELETE")
     */
    public function deletedocAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $document = $em->getRepository(document::class)->find($id);

        $em->remove($document);
        $em->flush();
        return $this->redirectToRoute('document_index');
    }

    /**
     * @Route("/page_emprunt/{id}", name="page_emprunt")
     */
    public function page_empruntAction(Request $request,$id)
    {

        $document = $this->getDoctrine()->getRepository(Document::class)->findBy(array('id' => $id));
        $emprunt = new emprunt();
        $document = $this->getDoctrine()->getRepository(Document::class)->find($id);

        $u = $this->getDoctrine()->getRepository(User::class)->find($id);
        $emprunt->setIdUser($u);
        $emprunt->setIddocument($document);

        $form = $this->createForm(EmpruntType::class, $emprunt);
        $form = $form->handleRequest($request);


        if ($form->isSubmitted()) {


            $em = $this->getDoctrine()->getManager();
            $em->persist($emprunt);
            $em->flush();
            return $this->redirectToRoute('showemprunt');
        }


        return $this->render('@Document/Emprunt/emprunter.html.twig', array("document" => $document, 'form' => $form->createView()));

    }

    /**
     * Creates a form to delete a document entity.
     *
     * @param Document $document The document entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Document $document)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('document_delete', array('id' => $document->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }

   }
