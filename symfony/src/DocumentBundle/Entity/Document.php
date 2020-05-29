<?php

namespace DocumentBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Document
 *
 * @ORM\Table(name="document")
 * @ORM\Entity(repositoryClass="DocumentBundle\Repository\DocumentRepository")
 */
class Document
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @var string
     *
     * @ORM\Column(name="nomdocument", type="string", length=255)
     */
    private $nomdocument;

    /**
     * @var string
     *
     * @ORM\Column(name="etatdocument", type="string", length=255)
     */
    private $etatdocument;


    /**
     * Get id
     *
     * @return int
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set nomdocument
     *
     * @param string $nomdocument
     *
     * @return Document
     */
    public function setNomdocument($nomdocument)
    {
        $this->nomdocument = $nomdocument;

        return $this;
    }

    /**
     * Get nomdocument
     *
     * @return string
     */
    public function getNomdocument()
    {
        return $this->nomdocument;
    }

    /**
     * Set etatdocument
     *
     * @param string $etatdocument
     *
     * @return Document
     */
    public function setEtatdocument($etatdocument)
    {
        $this->etatdocument = $etatdocument;

        return $this;
    }

    /**
     * Get etatdocument
     *
     * @return string
     */
    public function getEtatdocument()
    {
        return $this->etatdocument;
    }
    /**
     * @ORM\ManyToOne(targetEntity="categorie")
     *
     * @ORM\JoinColumn(name="categorie",referencedColumnName="id")
     */
    private $categorie;

    /**
     * @return mixed
     */
    public function getCategorie()
    {
        return $this->categorie;
    }

    /**
     * @param mixed $categorie
     */
    public function setCategorie($categorie)
    {
        $this->categorie = $categorie;
    }

}

